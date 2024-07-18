package ch.toyaxo.mcDiscordBot

import ch.toyaxo.mcDiscordBot.utils.serverUtils
import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.edit
import dev.kord.core.entity.channel.TextChannel
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import dev.kord.rest.builder.message.embed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

suspend fun main(args : Array<String>) {
    val key = args[0]
    val kord = Kord(key)

    val ip = args[1]
    val port = args[2].toInt()
    val checkTime: Long = args[3].toLong() // SECONDS

    val txtChannel: Long = args[4].toLong()
    val msgId: Long? = args.getOrNull(5)?.toLong()

    if(msgId == null){
        kord.getChannelOf<TextChannel>(Snowflake(txtChannel))?.createMessage("Message to Edit")
    }

    kord.launch {
        var state: Boolean? = null
        var nState: Boolean
        var cl: Color
        var msg: String

        while (true) {
            nState = serverUtils.checkServerStatus(ip, port)
            if(state == null || nState != state){
                state = nState
                val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                if(state){
                    msg = "Server ON 🟢\uFEFF [$time]"
                    cl = Color(0,255,0)
                }else{
                    msg = "Server OFF 🔴\uFEFF [$time]"
                    cl = Color(255,0,0)
                }
                msgId?.let { Snowflake(it) }?.let {
                    kord.getChannelOf<TextChannel>(Snowflake(txtChannel))?.getMessage(it)?.edit {
                        content = ""
                        embed {
                            title = msg
                            description = "(Check every $checkTime seconds)\n\nBot by toya.xo"
                            color = cl
                            url = "https://mcsrvstat.us/server/$ip:$port"
                        }
                    }
                }
            }
            delay(checkTime*1000)
        }
    }

    kord.login {
        // we need to specify this to receive the content of messages
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
        intents += Intent.DirectMessages
        println("Connected as ${kord.getSelf().username}!")
    }
}
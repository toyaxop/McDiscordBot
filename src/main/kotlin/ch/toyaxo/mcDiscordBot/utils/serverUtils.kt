package ch.toyaxo.mcDiscordBot.utils

import java.net.InetSocketAddress
import java.net.Socket

class serverUtils {
    companion object{
        fun checkServerStatus(ip: String, port: Int): Boolean {
            return try {
                Socket().use { socket ->
                    socket.connect(InetSocketAddress(ip, port), 2000) // Example for Minecraft server port 25565
                    true
                }
            } catch (e: Exception) {
                false
            }
        }
    }
}
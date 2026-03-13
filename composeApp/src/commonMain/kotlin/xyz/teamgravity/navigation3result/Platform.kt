package xyz.teamgravity.navigation3result

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
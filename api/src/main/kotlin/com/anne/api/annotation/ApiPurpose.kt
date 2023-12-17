package com.anne.api.annotation

annotation class ApiPurpose(val target: Array<Purpose>)

enum class Purpose {
    ADMIN,
    USER,
    DEVELOPER;
}

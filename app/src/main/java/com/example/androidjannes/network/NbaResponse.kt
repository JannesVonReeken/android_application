package com.example.androidjannes.network

data class NbaSeasonsResponse(
    val response: List<Int>
)

data class NbaStandingResponse(
    val get: String,
    val parameters: StandingParameters,
    val errors: List<String>,
    val results: Int,
    val response: List<StandingData>
)

data class StandingParameters(
    val league: String,
    val season: String
)

data class StandingData(
    val league: String,
    val season: Int,
    val team: TeamData,
    val conference: ConferenceData,
    val division: DivisionData,
    val win: WinData,
    val loss: LossData,
    val gamesBehind: String,
    val streak: Int,
    val winStreak: Boolean,
    val tieBreakerPoints: Any? // Du kannst den genauen Typ festlegen, wenn es bekannt ist
)

data class TeamData(
    val id: Int,
    val name: String,
    val nickname: String,
    val code: String,
    val logo: String
)

data class ConferenceData(
    val name: String,
    val rank: Int,
    val win: Int,
    val loss: Int
)

data class DivisionData(
    val name: String,
    val rank: Int,
    val win: Int,
    val loss: Int,
    val gamesBehind: String
)

data class WinData(
    val home: Int,
    val away: Int,
    val total: Int,
    val percentage: String,
    val lastTen: Int
)

data class LossData(
    val home: Int,
    val away: Int,
    val total: Int,
    val percentage: String,
    val lastTen: Int
)
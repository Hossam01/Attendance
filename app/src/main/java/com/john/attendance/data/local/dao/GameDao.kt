package com.john.attendance.data.local.dao

import androidx.room.*
import com.john.attendance.data.local.models.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Transaction
    @Query("Select * from game")
    fun getGameList(): Flow<List<Game>>

    @Query("delete from game where 1")
    suspend fun deleteAllGames()

    @Query("delete from game where game.GameID = :id")
    suspend fun deleteGame(id: Int)
}
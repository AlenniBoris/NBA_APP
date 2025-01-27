package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaGamesDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.toEntityModel
import com.alenniboris.nba_app.data.source.local.model.api.nba.toModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.GameEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiGamesDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NbaApiGamesDatabaseRepositoryImpl(
    private val nbaApiGamesDao: INbaGamesDao,
    private val dispatchers: IAppDispatchers
) : INbaApiGamesDatabaseRepository {

    override suspend fun addGameToDatabase(
        game: GameModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            user?.let {
                nbaApiGamesDao.addGameToDatabase(game.toEntityModel(it.userUid))
                return@withContext CustomResultModelDomain.Success(Unit)
            }
            return@withContext CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SavingElementError
            )
        }

    override suspend fun deleteGameFromDatabase(
        game: GameModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            user?.let {
                nbaApiGamesDao.deleteGameFromDatabase(game.toEntityModel(it.userUid))
                return@withContext CustomResultModelDomain.Success(Unit)
            }
            return@withContext CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SavingElementError
            )
        }

    override fun getAllGamesForUser(user: UserModelDomain): Flow<List<GameEntityModelDomain>> {
        return nbaApiGamesDao.getAllGamesForUser(user.userUid)
            .map { list -> list.map { it.toModelDomain() } }
    }

}
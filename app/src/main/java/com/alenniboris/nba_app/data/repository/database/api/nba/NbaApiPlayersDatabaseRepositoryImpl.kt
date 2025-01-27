package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaPlayersDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.toEntityModel
import com.alenniboris.nba_app.data.source.local.model.api.nba.toModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.entity.api.nba.PlayerEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiPlayersDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NbaApiPlayersDatabaseRepositoryImpl(
    private val nbaApiPlayersDao: INbaPlayersDao,
    private val dispatchers: IAppDispatchers
) : INbaApiPlayersDatabaseRepository {

    override suspend fun addPlayerToDatabase(
        player: PlayerModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            user?.let {
                nbaApiPlayersDao.addPlayerToDatabase(player.toEntityModel(it.userUid))
                return@withContext CustomResultModelDomain.Success(Unit)
            }
            return@withContext CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SavingElementError
            )
        }

    override suspend fun deletePlayerFromDatabase(
        player: PlayerModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            user?.let {
                nbaApiPlayersDao.deletePlayerFromDatabase(player.toEntityModel(it.userUid))
                return@withContext CustomResultModelDomain.Success(Unit)
            }
            return@withContext CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SavingElementError
            )
        }

    override fun getAllPlayersForUser(user: UserModelDomain): Flow<List<PlayerEntityModelDomain>> {
        return nbaApiPlayersDao.getAllPlayersForUser(user.userUid)
            .map { list -> list.map { it.toModelDomain() } }
    }

}
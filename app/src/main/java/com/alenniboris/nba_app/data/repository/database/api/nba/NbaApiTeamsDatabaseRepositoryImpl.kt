package com.alenniboris.nba_app.data.repository.database.api.nba

import com.alenniboris.nba_app.data.source.local.dao.api.nba.INbaTeamsDao
import com.alenniboris.nba_app.data.source.local.model.api.nba.toEntityModel
import com.alenniboris.nba_app.data.source.local.model.api.nba.toModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.TeamModelDomain
import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.model.entity.TeamEntityModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.repository.database.api.nba.INbaApiTeamsDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NbaApiTeamsDatabaseRepositoryImpl(
    private val nbaApiTeamsDao: INbaTeamsDao,
    private val dispatchers: IAppDispatchers
) : INbaApiTeamsDatabaseRepository {

    override suspend fun addTeamToDatabase(
        team: TeamModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            user?.let {
                nbaApiTeamsDao.addTeamToDatabase(team.toEntityModel(it.userUid))
                return@withContext CustomResultModelDomain.Success(Unit)
            }
            return@withContext CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SavingElementError
            )
        }

    override suspend fun deleteTeamFromDatabase(
        team: TeamModelDomain,
        user: UserModelDomain?
    ): CustomResultModelDomain<Unit, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            user?.let {
                nbaApiTeamsDao.deleteTeamFromDatabase(team.toEntityModel(it.userUid))
                return@withContext CustomResultModelDomain.Success(Unit)
            }
            return@withContext CustomResultModelDomain.Error(
                NbaApiExceptionModelDomain.SavingElementError
            )
        }

    override fun getAllTeamsForUser(user: UserModelDomain): Flow<List<TeamEntityModelDomain>> {
        return nbaApiTeamsDao.getAllTeamsForUser(user.userUid)
            .map { list -> list.map { it.toModelDomain() } }
    }

}
package com.alenniboris.nba_app.domain.model.filters

data class CountryModelDomain(
    override val id: Int,
    override val name: String,
    val code: String?,
    val flag: String?
) : IShowingFilterModel{
    constructor(
        name: String
    ): this(
        id = -1,
        name = name,
        code = null,
        flag = null
    )
}
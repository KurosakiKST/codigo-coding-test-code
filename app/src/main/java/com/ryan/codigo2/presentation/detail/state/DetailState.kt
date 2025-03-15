package com.ryan.codigo2.presentation.detail.state

import com.ryan.codigo2.domain.model.Movie

data class DetailState(
    val movie: Movie? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
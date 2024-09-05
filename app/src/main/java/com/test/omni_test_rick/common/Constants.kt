package com.test.omni_test_rick.common


data class CommonKeys(
    val characterIDKey: String = "id"
)

data class CommonTexts(
    val characterImage: String = "character.image.description",
    val commonUnderstoodLabel: String = "common.label.understood.text",
    val appNameBar: String = "app.name.bar"
)

data class DetailCharacterTexts(
    val characterStatusTitle: String = "character.detail.status.title",
    val characterSpeciesTitle: String = "character.detail.species.title",
    val characterOriginTitle: String = "character.detail.origin.title",
    val characterLocationTitle: String = "character.detail.location.title",
)
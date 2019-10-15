package com.abhishek.topgithub.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repository @JvmOverloads constructor(@SerializedName("username") private var _username : String? = null,
                                                @SerializedName("name") private val _name : String? = null,
                                                @SerializedName("url") private val _url : String? = null,
                                                @SerializedName("avatar") private val _avatar : String? = null,
                                                @SerializedName("repo") private val _repo : Repo? = null) : Serializable {

    val username: String?
        get() = _username

    val name: String?
        get() = _name

    val url: String?
        get() = _url

    val avatar: String?
        get() = _avatar

    val repo : Repo?
        get() = _repo
}


data class Repo @JvmOverloads constructor(@SerializedName("name") private val _name : String? = null,
                                          @SerializedName("description") private val _description : String? = null,
                                          @SerializedName("url") private val _url : String? = null ) {

    val name: String?
        get() = _name

    val description: String?
        get() = _description

    val url: String?
        get() = _url
}

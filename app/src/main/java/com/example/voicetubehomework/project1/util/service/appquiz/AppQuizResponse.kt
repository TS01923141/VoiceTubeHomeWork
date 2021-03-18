package com.example.voicetubehomework.project1.util.service.appquiz

import com.google.gson.annotations.SerializedName

/*
{
    "status": "success",
    "videos": [
        {
            "title": "IGN News - Kiefer Sutherland Playing Snake in Metal Gear Solid V",
            "img": "http://img.youtube.com/vi/S-FFgpUfVX8/maxresdefault.jpg"
        },
        {
            "title": "Taylor Swift - New Romantics",
            "img": "http://img.youtube.com/vi/wyK7YuwUWsU/maxresdefault.jpg"
        },
        {
            "title": "Great White Sharks 360 Video 4K!! - Close encounter on Amazing Virtual Dive",
            "img": "http://img.youtube.com/vi/HNOT_feL27Y/maxresdefault.jpg"
        }
    ]
}
 */
data class AppQuizResponse (
    @SerializedName("status") val status: String,
    @SerializedName("videos") val videos: MutableList<Video>
)
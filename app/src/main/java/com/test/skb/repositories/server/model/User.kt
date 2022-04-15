package com.test.skb.repositories.server.model

import com.google.gson.annotations.SerializedName

class User (
   var login: String,
   var id: Int,
   @SerializedName("node_id")
   var nodeId: String,
   @SerializedName("avatar_url")
   var avatarUrl: String,
   @SerializedName("gravatar_id")
   var gravatarId: String,
   var url: String,
   @SerializedName("html_url")
   var htmlUrl: String,
   @SerializedName("followers_url")
   var followersUrl: String,
   @SerializedName("following_url")
   var followingUrl: String,
   @SerializedName("gists_url")
   var gistsUrl: String,
   @SerializedName("starred_url")
   var starredUrl: String,
   @SerializedName("subscriptions_url")
   var subscriptionsUrl: String,
   @SerializedName("organizations_url")
   var organizationsUrl: String,
   @SerializedName("repos_url")
   var reposUrl: String,
   @SerializedName("events_url")
   var eventsUrl: String,
   @SerializedName("received_events_url")
   var receivedEventsUrl: String,
   var type: String,
   @SerializedName("site_admin")
   var siteAdmin: Boolean,
   var name: String,
   var company: String,
   var blog: String,
   var location: String,
   var email: String,
   var hireable: String,
   var bio: String,
   @SerializedName("twitter_username")
   var twitterUsername: String,
   @SerializedName("public_repos")
   var publicRepos: Int,
   @SerializedName("public_gists")
   var publicGists: Int,
   var followers: Int,
   var following: Int,
   @SerializedName("created_at")
   var createdAt: String,
   @SerializedName("updated_at")
   var updatedAt: String
)
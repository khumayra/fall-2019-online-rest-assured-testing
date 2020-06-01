package com.automation.homework.git_hub_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 {
        "id": 10664,
        "node_id": "MDEwOlJlcG9zaXRvcnkxMDY2NA==",
        "name": "cucumber-ruby",
        "full_name": "cucumber/cucumber-ruby",
        "private": false,
        "owner": {
            "login": "cucumber",
            "id": 320565,
            "node_id": "MDEyOk9yZ2FuaXphdGlvbjMyMDU2NQ==",
            "avatar_url": "https://avatars0.githubusercontent.com/u/320565?v=4",
            "gravatar_id": "",
            "url": "https://api.github.com/users/cucumber",
            "html_url": "https://github.com/cucumber",
            "followers_url": "https://api.github.com/users/cucumber/followers",
            "following_url": "https://api.github.com/users/cucumber/following{/other_user}",
            "gists_url": "https://api.github.com/users/cucumber/gists{/gist_id}",
            "starred_url": "https://api.github.com/users/cucumber/starred{/owner}{/repo}",
            "subscriptions_url": "https://api.github.com/users/cucumber/subscriptions",
            "organizations_url": "https://api.github.com/users/cucumber/orgs",
            "repos_url": "https://api.github.com/users/cucumber/repos",
            "events_url": "https://api.github.com/users/cucumber/events{/privacy}",
            "received_events_url": "https://api.github.com/users/cucumber/received_events",
            "type": "Organization",
            "site_admin": false
        }
 */
public class Repos {
    private int id;
    private String node_id;
    private String name;
    private String full_name;
    private Owner owner;
   @SerializedName("private")
   @Expose
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Repos{" +
                "id=" + id +
                ", node_id='" + node_id + '\'' +
                ", name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", owner=" + owner +
                ", status=" + status +
                '}';
    }
}

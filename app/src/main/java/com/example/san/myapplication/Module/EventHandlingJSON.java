package com.example.san.myapplication.Module;

import org.json.JSONObject;

/**
 * Created by vaps on 9/21/2017.
 */

public class EventHandlingJSON
{
    JSONObject jsonObject;

    public EventHandlingJSON(JSONObject initialize)
    {
        jsonObject = initialize;
    }

    public JSONObject jsonObject()
    {
        return jsonObject;
    }
}

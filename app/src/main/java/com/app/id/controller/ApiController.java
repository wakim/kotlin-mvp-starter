package com.app.id.controller;

import com.app.id.api.ApiService;
import com.app.id.application.Application;

public class ApiController extends BaseController {

    protected ApiService apiService;

    public ApiController(Application app, ApiService apiService, PreferencesManager preferencesManager) {
        super(app, preferencesManager);
        this.apiService = apiService;
    }
}

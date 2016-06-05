package com.app.id.controller

import com.app.id.api.ApiService
import com.app.id.application.Application

class ApiController(app: Application, var apiService: ApiService, preferencesManager: PreferencesManager): BaseController(app, preferencesManager) {

}
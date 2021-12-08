const express = require('express')
const router = new express.Router()

const Profile = require('../models/profile')
const User = require('../models/user')
const Structure = require('../models/structure')

module.exports = router
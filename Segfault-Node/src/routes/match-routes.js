const express = require('express')
const router = new express.Router()

const Profile = require('../models/profile')
const User = require('../models/user')
const Match = require('../models/match')

module.exports = router
const express = require('express')
const router = new express.Router()

const Profile = require('../models/profile')
const User = require('../models/user')
const Promoter = require('../models/promoter')

module.exports = router
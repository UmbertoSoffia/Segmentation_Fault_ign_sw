const express = require('express')
const router = new express.Router()
const jwt = require('jsonwebtoken')

const Promoter = require('../models/promoter')

router.post('/authenticate/email', async (req, res, next) => {
  const {
    email, password, language
  } = req.body
  try {
    const prom = await Promoter.findOne({ email })
    if (!prom) {
      return res.status(401).send('Authentication failure')
    }
    const passwordMatch = await prom.comparePassword(password)
    if (!passwordMatch) {
      return res.status(401).send('Authentication failure')
    }
    const token = jwt.sign(
      { user_id: prom.promoter_id, email },
      process.env.SERVER_SECRET
    )
    const response = {
      id: prom.promoter_id,
      email,
      name: prom.name,
      token
    }
    prom.last_login = new Date()
    prom.language = language
    prom.token = token
    
    await prom.save()
    res.json(response)
  } catch (error) {
    next(error)
  }
})

module.exports = router
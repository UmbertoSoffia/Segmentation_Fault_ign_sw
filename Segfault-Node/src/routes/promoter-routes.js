const express = require('express')
const router = new express.Router()
const jwt = require('jsonwebtoken')
const objectid = require('objectid')

const Promoter = require('../models/promoter')
const Address = require('../models/address')

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

router.post('/', async (req, res, next) => {
  const {
    name, email, password, language, addr
  } = req.body
  if (!(name && email && password &&  language && addr)) {
    return res.status(400).send('Bad Request')
  }
  try {
    const prom = await Promoter.findOne({ email })
    if (prom) {
      return res.status(409).send('Promoter already exists')
    }
    const promoter_id = objectid()
	const user_id = promoter_id
    const address_id = objectid()
    const token = jwt.sign({ user_id, email }, process.env.SERVER_SECRET)
    const newPromoter = {
      promoter_id,
	  name,
      email,
      token,
      password,
      language,
	  address_id,
      last_login: new Date()
    }
    const address = {
      address_id,
      street: addr,
      number: '',
      city: ''
    }
    await Promoter.create(newPromoter)
    await Address.create(address)
    const response = {
      id: user_id,
      email,
      name: name,
      token
    }
    res.json(response)
  } catch (err) {
    next(err)
  }
})

module.exports = router
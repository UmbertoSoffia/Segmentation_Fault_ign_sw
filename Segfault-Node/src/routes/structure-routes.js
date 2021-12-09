const express = require('express')
const router = new express.Router()
const jwt = require('jsonwebtoken')

const Address = require('../models/address')
const Promoter = require('../models/promoter')
const Structure = require('../models/structure')
const objectid = require('objectid')

router.post('/', async (req, res, next) => {
  const {
    name, description, start_time, stop_time, addr, working_days, number, token
  } = req.body
  if (!(name && addr && number && token)) {
    return res.status(400).send('Bad Request')
  }
  try {
    const struct = await Structure.findOne({ name })
    if (struct) {
      return res.status(409).send('Structure already exists')
    }
    const structure_id = objectid()
    const address_id = objectid()
	const { user_id, email } = await jwt.verify(token, process.env.SERVER_SECRET)
	const promoter_id = user_id
	
	console.log(`Promoter id: ${promoter_id}`)
	
    const newStructure = {
      structure_id,
	  name,
      description,
      start_time,
      stop_time,
	  address_id,
	  number,
      promoter_id
    }
    const address = {
      address_id,
      street: addr,
      number: '',
      city: ''
    }
    await Structure.create(newStructure)
    await Address.create(address)
    const response = {
      id: structure_id
    }
    res.json(response)
  } catch (err) {
    next(err)
  }
})

module.exports = router
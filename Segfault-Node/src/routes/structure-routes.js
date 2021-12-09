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

router.get('/', (req, res, next) => {
	const { user_id, email } = jwt.verify(req.query.token, process.env.SERVER_SECRET)
  if (!user_id) { return res.status(401).send('Not authenticated') }
  const promoter_id = req.query.promoter
  if(!promoter_id){ return res.status(400).send('Bad request')}
  Structure.find({ promoter_id })
  .populate('address')
  .lean()
  .exec()
  .then(structures => {
	  if(structures.length === 0){return res.status(404).send('Structures not found')}
      res.json(structures)
    }).catch(next)
})

module.exports = router
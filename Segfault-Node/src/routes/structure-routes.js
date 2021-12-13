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
      promoter_id,
	  working_days
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
  if(!promoter_id){ 
	Structure.find({})
    .populate('address')
    .lean()
    .exec()
    .then(structures => {
	    if(structures.length === 0){return res.status(404).send('Structures not found')}
	    res.json(structures)
	}).catch(next)
  }
  else{
  Structure.find({ promoter_id })
  .populate('address')
  .lean()
  .exec()
  .then(structures => {
	  if(structures.length === 0){return res.status(404).send('Structures not found')}
	  res.json(structures)
	}).catch(next)
  }
})

router.get('/:id', (req, res, next) => {
	const { user_id, email } = jwt.verify(req.query.token, process.env.SERVER_SECRET)
  if (!user_id) { return res.status(401).send('Not authenticated') }
  const structure_id = req.params.id
  Structure.findOne({ structure_id })
  .populate('address')
  .lean()
  .exec()
  .then(structure => {
	  if(!structure){return res.status(404).send('Structure not found')}
      res.json(structure)
    }).catch(next)
})

router.delete('/:id', async (req, res, next) => {
  if (!req.user_id) {
    return res.status(401).send('Not authenticated')
  }
  const structure_id = req.params.id
 
  try {
    const struct = await Structure.findOneAndDelete({ structure_id })
    res.status(200).send('Structure was deleted')
  } catch (error) {
    next(error)
  }
})

router.post('/modify', async (req, res, next) => {
  const {
    id, description, start_time, stop_time, addr_id, working_days, number, street
  } = req.body
  if (!(id && addr_id && description && start_time && stop_time && working_days && number && street)) {
    return res.status(400).send('Bad Request')
  }
  try {
	const structure_id = req.body.id
	const address_id = req.body.addr_id
    const struct = await Structure.findOne({ structure_id })
    if (!struct) {
      return res.status(404).send('Structure not found')
    }
	
    const patchStructure = {
      description,
      start_time,
      stop_time,
	  number, 
	  working_days
    }
    const address = {
      street
    }
    await Structure.updateOne({ structure_id }, patchStructure)
    await Address.updateOne({ address_id }, address)
    const response = {
      ok: true
    }
    res.json(response)
  } catch (err) {
    next(err)
  }
})

module.exports = router

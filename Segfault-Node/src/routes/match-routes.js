const express = require('express')
const router = new express.Router()
const jwt = require('jsonwebtoken')

const Structure = require('../models/structure')
const Match = require('../models/match')
const objectid = require('objectid')

router.post('/', async (req, res, next) => {
  const {
    name, description, start_time, stop_time, structure_id, date, number, token, age_range, sport, creator_id, creator_type
  } = req.body
  if (!(name && start_time && stop_time && token && structure_id && date && number && age_range && sport && creator_id && creator_type)) {
    return res.status(400).send('Bad Request')
  }
  try {
    
    const match_id = objectid()
	
    const newMatch = {
      match_id,
	  name,
      description,
      start_time,
      stop_time,
	  structure_id,
	  creator_id,
	  creator_type,
	  date,
	  number,
      age_range,
	  sport
    }
    await Match.create(newMatch)
    const response = {
      id: match_id
    }
    res.json(response)
  } catch (err) {
    next(err)
  }
})

router.get('/', (req, res, next) => {
	const { user_id, email } = jwt.verify(req.query.token, process.env.SERVER_SECRET)
  if (!user_id) { return res.status(401).send('Not authenticated') }
	Match.find({})
    .populate('structure')
    .lean()
    .exec()
    .then(matches => {
	    if(matches.length === 0){return res.status(404).send('Matches not found')}
	    res.json(matches)
	}).catch(next)
  
})

/*router.get('/:id', (req, res, next) => {
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
})*/

module.exports = router

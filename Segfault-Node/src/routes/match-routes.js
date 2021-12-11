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
	const id = req.query.id
	const type = req.query.type
    if (!user_id) { return res.status(401).send('Not authenticated') }
	if(!id){
		Match.find({})
		.populate('structure')
		.lean()
		.exec()
		.then(matches => {
			if(matches.length === 0){return res.status(404).send('Matches not found')}
			res.json(matches)
		}).catch(next)
    }
	else{
		if(!type) {return res.status(400).send('Bad request')}
		const creator_id = id
		const creator_type = type
		Match.find({creator_id, creator_type})
		.populate('structure')
		.lean()
		.exec()
		.then(matches => {
			if(matches.length === 0){return res.status(404).send('Matches not found')}
			res.json(matches)
		}).catch(next)
	}
})

router.get('/:id', (req, res, next) => {
	const { user_id, email } = jwt.verify(req.query.token, process.env.SERVER_SECRET)
  if (!user_id) { return res.status(401).send('Not authenticated') }
  const match_id = req.params.id
  Match.findOne({ match_id })
  .populate('structure')
  .lean()
  .exec()
  .then(match => {
	  if(!match){return res.status(404).send('Match not found')}
      res.json(match)
    }).catch(next)
})

router.delete('/:id', async (req, res, next) => {
  if (!req.user_id) {
    return res.status(401).send('Not authenticated')
  }
  const match_id = req.params.id
 
  try {
    const match = await Match.findOneAndDelete({ match_id })
    res.status(200).send('Match was deleted')
  } catch (error) {
    next(error)
  }
})

router.post('/modify', async (req, res, next) => {
  const {
    id, description, start_time, stop_time, structure_id, date, number, age_range, sport, name
  } = req.body
  if (!(id && structure_id && description && start_time && stop_time && date && number && age_range && sport && name)) {
    return res.status(400).send('Bad Request')
  }
  try {
	const match_id = req.body.id
    const match = await Match.findOne({ match_id })
    if (!match) {
      return res.status(404).send('Structure not found')
    }
	
    const patchMatch = {
      description,
	  name,
      start_time,
      stop_time,
	  number, 
	  structure_id,
	  date,
	  age_range,
	  sport
    }
    await Match.updateOne({ match_id }, patchMatch)
    const response = {
      ok: true
    }
    res.json(response)
  } catch (err) {
    next(err)
  }
})

module.exports = router

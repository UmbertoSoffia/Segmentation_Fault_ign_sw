const express = require('express')
const router = new express.Router()
const jwt = require('jsonwebtoken')

const Structure = require('../models/structure')
const Reservation = require('../models/reservation')
const objectid = require('objectid')

router.post('/', async (req, res, next) => {
  const {
    match_id, user_id
  } = req.body
  if (!(match_id && user_id)) {
    return res.status(400).send('Bad Request')
  }
  try {
    const reservation = await Reservation.findOne({ match_id, user_id })
    if (reservation) {
      return res.status(409).send('Reservation already exists')
    }
    const reservation_id = objectid()
	
    const newReservation = {
      match_id,
	  reservation_id,
	  user_id
    }
    await Reservation.create(newReservation)
    const response = {
      id: reservation_id
    }
    res.json(response)
  } catch (err) {
    next(err)
  }
})

router.get('/', (req, res, next) => {
	const { user_id, email } = jwt.verify(req.query.token, process.env.SERVER_SECRET)
  if (!user_id) { return res.status(401).send('Not authenticated') }
  const user = req.query.user
  if(!user){
	Reservation.find({})
    .populate('user')
	.populate('match')
    .lean()
    .exec()
    .then(reservations => {
	    if(reservations.length === 0){return res.status(404).send('Reservations not found')}
	    res.json(reservations)
	}).catch(next)
  }
  else{
	Reservation.find({user_id})
    .populate('user')
	.populate('match')
    .lean()
    .exec()
    .then(reservations => {
	    if(reservations.length === 0){return res.status(404).send('Reservations not found')}
	    res.json(reservations)
	}).catch(next)
  }
})

router.get('/:id', (req, res, next) => {
	const { user_id, email } = jwt.verify(req.query.token, process.env.SERVER_SECRET)
  if (!user_id) { return res.status(401).send('Not authenticated') }
  const reservation_id = req.params.id
  Reservation.findOne({ reservation_id })
  .populate('user')
  .populate('match')
  .lean()
  .exec()
  .then(reservation => {
	  if(!reservation){return res.status(404).send('Reservation not found')}
      res.json(reservation)
    }).catch(next)
})

router.delete('/:id', async (req, res, next) => {
  if (!req.user_id) {
    return res.status(401).send('Not authenticated')
  }
  const reservation_id = req.params.id
 
  try {
    const reservation = await Reservation.findOneAndDelete({ reservation_id })
    res.status(200).send('Reservation was deleted')
  } catch (error) {
    next(error)
  }
})

module.exports = router

const express = require('express')
const router = new express.Router()
const jwt = require('jsonwebtoken')

const nodemailer = require('nodemailer')
const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'segfaultunive@gmail.com',
    pass: 'Unive2022'
  }
})

const Match = require('../models/match')
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
  const { user_id, email } = jwt.verify(req.query.token, process.env.SERVER_SECRET)
  if (!user_id) {
    return res.status(401).send('Not authenticated')
  }
  const reservation_id = req.params.id
 
  try {
    const reservation = await Reservation.findOneAndDelete({ reservation_id })
    const response = {
      ok: true
    }
    res.json(response)
  } catch (error) {
    next(error)
  }
})

router.post('/notify', async (req, res, next) => {
  const {user_id} = req.body
  if (!(user_id)) {
    return res.status(400).send('Bad Request')
  }
  try {
    const reservations = await Reservation.find({user_id })
	.populate('match')
	.lean()
    .exec()
    .then(reservations => {
	    if(reservations.length === 0){return res.status(404).send('Reservations not found')}
	})
	
	reservations.forEach(reservation =>{
		const match = reservation.match
		const match_id = match.match_id
		const date_array = match.date.split("-")
		const date_string = date_array[2] + "-" +  date_array[1] + "-" + date_array[0]
		const date = Date.parse(date_string)
		if(date <= new Date() && date.getDate() >= new Date().getDate() - 10 ){
			Reservation.find({match_id})
			.populate("user")
			.lean()
			.exec()
			.then(reservs => {
				if(reservs.length === 0){return res.status(404).send('Reservations not found')}
				reservs.forEach(reserv =>{
					const user = reserv.user
					const mailOptions = {
					  from: 'segfaultunive@gmail.com',
					  to: user.email,
					  subject: 'Notifica di positività',
					  html: 'La informiamo che ha partecipato a un match di cui uno dei partecipanti ha notificato la propria positività al COVID-19.<br>Si raccomanda di fare il tampone quanto prima.'
					}
					transporter.sendMail(mailOptions, function(err, info){
						if(err){return res.status(500).send('Email not sent')}
					})
				})
			})
		}
	})
    const response = {
      ok: true
    }
    res.json(response)
  } catch (err) {
    next(err)
  }
})

module.exports = router

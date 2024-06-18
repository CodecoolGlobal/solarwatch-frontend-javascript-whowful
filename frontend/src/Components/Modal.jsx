import React from 'react'
import "./Modal.css"

function Modal({ solarData, closeModal }) {
  return (
    <div className='modalBackground'>
      <div className='modalContainer'>
        <div className='title'>
          <h1>{solarData.city}</h1>
        </div>
        <div className='body'>
          <h2>Sunrise: {solarData.sunrise}</h2>
          <h2>Sunset: {solarData.sunset}</h2>
        </div>
        <div className='footer'>
          <button onClick={() => closeModal(false)}>Close</button>
        </div>
      </div>
    </div>
  )
}

export default Modal;
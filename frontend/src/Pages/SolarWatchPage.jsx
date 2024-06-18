import React, { useState } from "react";
import './SolarWatchPage.css'
import '../Components/Layout.css'
import Modal from '../Components/Modal'


const getSolarData = async (city, date) => {
  try {
    const res = await fetch(`/api/solarwatch?city=${city}&date=${date}`)
    if (!res.ok) {
      console.log(`Response status: ${res.status}`)
    }
    return await res.json()
  } catch (error) {
    console.error("Error fetching Solar Data: ", error)
  }
}

const SolarWatchPage = () => {
  const [cityName, setCityName] = useState('')
  const [solarData, setSolarData] = useState(null)
  const [openModal, setOpenModal] = useState(false)

  const handleChange = (e) => {
    console.log(e.target.value)
    setCityName(e.target.value)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const today = new Date();
    const formattedDate = today.toISOString().split('T')[0];
    const response = await getSolarData(cityName, formattedDate)
    console.log(response)
    setSolarData(response)
    setOpenModal(true)
  }

  return (
    <>
      {!openModal ? (
        <div style={{
          backgroundImage: "url(/maxresdefault.jpg)",
          backgroundSize: "cover",
          backgroundPosition: "center",
          width: "100vw",
          height: "100vh",
          margin: 0,
          padding: 0,
        }}>
          <label className="swInputLabel"> Please enter the city:
            <input
              type="text"
              className="solarWatch-input"
              name="solarWatch"
              value={cityName}
              onChange={handleChange}
            />
          </label>
          <button className="openModalBtn" type="submit" onClick={handleSubmit}>Search</button>
        </div>
      ) : (
      <Modal solarData={solarData} closeModal={setOpenModal} />
      )}
    </>
  );
};

export default SolarWatchPage;
import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import HeaderComponent from './components/HeaderComponent'
import ListEmployeeComponent from './components/ListEmployeeComponent'
import FooterComponent from './components/FooterComponent'


function App() {


  return (
    <>
      <HeaderComponent/>
      <ListEmployeeComponent />
      <FooterComponent />
    </>
  )
}

export default App
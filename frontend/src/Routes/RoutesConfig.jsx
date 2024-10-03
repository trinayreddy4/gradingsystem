import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Login from '../pages/Login/Login'
import Register from '../pages/Register/Register'
import Home from '../pages/Home/Home'
import Upload from '../pages/Upload/Upload'
import FacultyGrading from '../pages/GradePosting/FacultyGrading'
const RoutesConfig = () => {
  return (
    <>
        <Routes>
            <Route path='/login' element={<Login/>}></Route>
            <Route path='/register' element={<Register/>}></Route>
            <Route path='/' element={<Home/>}></Route>
            <Route path='/upload' element={<Upload/>}></Route>
            <Route path="/gradepost" element={<FacultyGrading/>} />
        </Routes>
    </>
  )
}

export default RoutesConfig
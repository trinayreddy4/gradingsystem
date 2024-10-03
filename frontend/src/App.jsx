import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import RoutesConfig from './Routes/RoutesConfig'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div
        className="h-full w-full"
      >
        <RoutesConfig/>
       </div>
    </>
  )
}

export default App

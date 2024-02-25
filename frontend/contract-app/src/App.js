import React, { useState , useRef} from 'react'
import 'bootstrap/dist/css/bootstrap.css'
import FileInput from  './components/FileInput'
import NavBarComp from './components/Navbar'
import SpinnerWithText from './components/SpinnerWithText'
import Alert from './components/Alert'
import './App.css'
import api from './api/api'

function App() {
  const [answer,setAnswer] = useState([]);
  const [isShow,setShow] = useState(false);
  const [showError,setShowError] = useState(false);

  const fetchResponse = async () =>{
    console.log(ref.current.value);
    const question = ref.current.value;
    setShow(true)
    try {
      const response = await api.get('/getanswer?question='+question);
      console.log(response.data);
      setAnswer(response.data)
      setShow(false)
    } catch(error){
      console.log("Error: "+error);
      setShow(false)
      setShowError(true)
    }
    
  }
  const ref = useRef(null);

  return (
    <React.Fragment>
       
      
      
      <main className='app'>
        <form >
        <NavBarComp />
        <FileInput />
            <div>
              <h3 className='m-3'>Lizzy AI</h3>
            </div>

            {isShow && <SpinnerWithText />}

            {showError && <Alert />}


            <button type="button" className="btn btn-primary mb-4 m-3" onClick={fetchResponse}>Evaluate RAG</button> 


        </form>
      </main>

    </React.Fragment>
  )
}

export default App

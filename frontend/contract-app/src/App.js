import React, { useState} from 'react'
import 'bootstrap/dist/css/bootstrap.css'
import FileInput from  './components/FileInput'
import NavBarComp from './components/Navbar'
import SpinnerWithText from './components/SpinnerWithText'
import Alert from './components/Alert'
import './App.css'
import api from './api/api'
import Tables from './components/Tables'

function App() {
  const [evaluation,setEvaluation] = useState([]);
  const [isShow,setShow] = useState(false);
  const [showError,setShowError] = useState(false);

  const fetchResponse = async () =>{
    setShow(true)
    try {
      const response = await api.get('/getevaluation?type='+1);
      console.log(response.data);
      setEvaluation(response.data)
      setShow(true)
    } catch(error){
      console.log("Error: "+error);
      setShow(false)
      setShowError(true)
    }
    
  }

  return (
    <React.Fragment>
       
      
      
      <main className='app'>
        <form >
        <NavBarComp />
        <FileInput />
           

            {isShow &&  
              <div>
                <h3 className='m-3'>Evaluation Response</h3>
                <Tables evaluations={evaluation}/>
              </div>
            }

            {showError && <Alert />}


            <button type="button" className="btn btn-primary mb-4 m-3" onClick={fetchResponse}>Evaluate RAG</button> 


        </form>
      </main>

    </React.Fragment>
  )
}

export default App

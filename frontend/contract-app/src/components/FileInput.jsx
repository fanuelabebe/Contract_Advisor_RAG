import React from 'react';
import 'bootstrap/dist/css/bootstrap.css'
function FileInput (){
    return(
        <div>
            <div className="mb-5">
                <h3 className='m-3'>Select file for context</h3>
                <input type="file" className="form-control m-3" id="inputGroupFile02"/>
            </div>
        </div>
      
    );
}


export default FileInput;
import React from 'react';
import 'bootstrap/dist/css/bootstrap.css'
function SpinnerWithText () {
    return(
        <div>
            <button className="btn btn-primary m-3" type="button" disabled>
                <span className="spinner-grow spinner-grow-sm" aria-hidden="true"></span>
                <span role="status">Loading...</span>
            </button>
        </div>
      
    );
}


export default SpinnerWithText;
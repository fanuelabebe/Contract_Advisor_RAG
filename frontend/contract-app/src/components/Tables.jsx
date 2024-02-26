
import { MDBBadge, MDBBtn, MDBTable, MDBTableHead, MDBTableBody } from 'mdb-react-ui-kit';
function Tables (props){
    return (
        <MDBTable align='middle'>
            <MDBTableHead>
                <tr>
                <th scope='col'>Context Percision</th>
                <th scope='col'>Context Recall</th>
                <th scope='col'>Faithfulness</th>
                <th scope='col'>Answer Relevancy</th>
                </tr>
            </MDBTableHead>
            <MDBTableBody>
                {props.evaluations.map(evaluation =>(
                    <tr>
                        <td>
                            <div className='d-flex align-items-center'>
                          
                            <div className='ms-3'>
                                <p className='fw-bold mb-1'>{evaluation.context_precision}</p>
                            </div>
                            </div>
                        </td>
                        <td>
                            <p className='fw-normal mb-1'>{evaluation.context_recall}</p>
                        </td>
                        <td>{evaluation.faithfulness}</td>
                        <td>{evaluation.answer_relevancy}</td>
                    </tr>
                ))}
                
            
            </MDBTableBody>
        </MDBTable>
    );
    
}
 
export default Tables;

import { MDBBadge, MDBBtn, MDBTable, MDBTableHead, MDBTableBody } from 'mdb-react-ui-kit';
import api from '../api/api'
function Tables (props){
    
    // const [tables,setTables] = useState([]);

    // useEffect(() =>{
    //     fetchTables();
    // });

    // const fetchTables = async () =>{
    //     // const tables_r = [["Name","Title","Status","Position","Action"],["Name","Title","Status","Position","Action"],["Name","Title","Status","Position","Action"]];
    //     // setTables(tables_r)
    //     const response = await api.get('/ranks?prompt=who am i');
    //     console.log(response.data);
    //     setTables(response.data)
    // }

    
    return (
        
        <MDBTable align='middle'>
            <MDBTableHead>
                <tr>
                <th scope='col'>Id</th>
                <th scope='col'>Rank Name</th>
                <th scope='col'>Rank Value</th>
                </tr>
            </MDBTableHead>
            <MDBTableBody>
                {props.tables.map(table =>(
                    <tr>
                        <td>
                            <div className='d-flex align-items-center'>
                          
                            <div className='ms-3'>
                                <p className='fw-bold mb-1'>{table.id}</p>
                            </div>
                            </div>
                        </td>
                        <td>
                            <p className='fw-normal mb-1'>{table.name}</p>
                        </td>
                        <td>{table.rating}</td>
                    </tr>
                ))}
                
            
            </MDBTableBody>
        </MDBTable>
    );
    
}
 
export default Tables;
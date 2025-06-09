import React, { useState } from 'react'

const EmployeeComponent = () => {

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
//using arrow function
    const handleFirstName = (e) => setFirstName(e.tager.value);
    const handleLastName = (e) => setLastName(e.tager.value);
    const handleEmail = (e) => setEmail(e.tager.value);
    // function handleFirstName(e)
    // {
    //     setFirstName(e.target.value);
    // }

    // function handleLastName(e)
    // {
    //     setLastName(e.target.value);
    // }

    // function handleEmail(e)
    // {
    //     setEmail(e.target.value);
    // }

    function saveEmployee(e)
    {
        e.preventDefault();
        const employee = {firstName, lastName, email}
        console.log(employee)
    }

  return (
    <div  className='container'>
        <br></br>
        <div className='row col-md-6 offset-md-3'>
            <div className='card'>
                <h2 className='text-center'>Add Employee</h2>
                <form>

                    <div className='form-group mb-2'>
                        <label className='form-label'>Employee First Name:</label>
                        <input 
                            type='text' 
                            placeholder='Enter First Name' 
                            name="firstName"
                            value={firstName}
                            className='form-control'
                            onChange={handleFirstName} //(e) => setFirstName(e.tager.value) -> const handleFirstName = (e) => setFirstName(e.tager.value);
                        />
                    </div>

                    <div className='form-group mb-2'>
                        <label className='form-label'>Employee Last Name:</label>
                        <input 
                            type='text' 
                            placeholder='Enter Last Name' 
                            name="lastName"
                            value={lastName}
                            className='form-control'
                            onChange={handleLastName}
                        />
                    </div>

                    <div className='form-group mb-2'>
                        <label className='form-label'>Employee Email:</label>
                        <input 
                            type='text' 
                            placeholder='Enter Email' 
                            name="email"
                            value={email}
                            className='form-control'
                            onChange={handleEmail}
                        />
                    </div>

                    <button className='btn btn-success' onClick={saveEmployee}> Submit </button>
                </form>
                <br></br>
            </div>
        </div>
        <br></br>
    </div>
  )
}
export default EmployeeComponent
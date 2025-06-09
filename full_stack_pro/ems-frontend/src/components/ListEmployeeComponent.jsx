import React from 'react'

const ListEmployeeComponent = () => {
    const dummydata = [
        {
            "id":1,
            "firstName":"jaya",
            "lastName":"bala",
            "email":"jayabala4@gmail.com"
        },
        {
            "id":2,
            "firstName":"tharun",
            "lastName":"bala",
            "email":"tharunbala4@gmail.com"
        },
        {
            "id":3,
            "firstName":"jaya",
            "lastName":"balajee",
            "email":"jayabalajee4@gmail.com"
        }
    ]
  return (
    <div className='container'>
        <h2 className='text-center'> List Of Employees </h2>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th> Employee ID</th>
                    <th> Employee First Name</th>
                    <th> Employee Last Name</th>
                    <th> Employee Email ID</th>
                </tr>
            </thead>
            <tbody>
                {
                    dummydata.map(employee => 
                        <tr key={employee.id}>

                            <td>{employee.id}</td>
                            <td>{employee.firstName}</td>
                            <td>{employee.lastName}</td>
                            <td>{employee.email}</td>

                        </tr>
                    )
                }
            </tbody>
        </table>
    </div>
  )
}

export default ListEmployeeComponent
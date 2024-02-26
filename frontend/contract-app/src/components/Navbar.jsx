import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import React from 'react';
import logo from '../assets/10Academy.png'

function NavBarComp() {
  return (
    <Navbar expand="lg" className="bg-body-tertiary container-fluid mb-5">
      <Container >
        <img src={logo} width={80} height={80} />
        <Navbar.Brand href="#home" className='m-3'>Lizzy AI's Advanced RAG Contract Advisor</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <NavDropdown title="Select Chunking Type" id="basic-nav-dropdown">
                <NavDropdown.Item >Chunking Type 1</NavDropdown.Item>
                <NavDropdown.Item >Chunking Type 2</NavDropdown.Item>
                <NavDropdown.Item >Chunking Type 3</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBarComp;
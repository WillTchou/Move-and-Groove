import React from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link } from "react-router-dom";
import { authService } from "./authService";
import { useNavigate } from "react-router-dom";

export const Header = () => {
	const navigate = useNavigate();

	const logout = () => {
		authService.logout();
		navigate("/");
	};

	return (
		<Navbar bg="dark" variant="dark">
			<Container>
				<Navbar.Brand href="/">Move and Grove</Navbar.Brand>
				<Nav className="me-auto">
					<Nav.Link>
						<Link to="/">Home</Link>
					</Nav.Link>
					{authService.isLogged() && (
						<Nav.Link>
							<Link to="/myAccount">My Account</Link>
						</Nav.Link>
					)}
					{!authService.isLogged() && (
						<Nav.Link>
							<Link to="/signIn">Sign In</Link>
						</Nav.Link>
					)}
					{!authService.isLogged() && (
						<Nav.Link>
							<Link to="/signUp">Sign Up</Link>
						</Nav.Link>
					)}
					{authService.isLogged() && (
						<Nav.Link>
							<Link onClick={logout}>Logout</Link>
						</Nav.Link>
					)}
				</Nav>
			</Container>
		</Navbar>
	);
};

import React, { useState, useEffect } from "react";
import Card from "react-bootstrap/Card";
import { userService } from "./userService";
import { Header } from "./Header";
import "./css/MyAccount.css";
import { authService } from "./authService";
import jwt_decode from "jwt-decode";

export const MyAccount = () => {
	const [user, setUser] = useState([]);

	let getUsernameFromToken = () => {
		return jwt_decode(authService.getToken()).sub;
	};

	useEffect(() => {
		userService
			.getUserByUsername(getUsernameFromToken())
			.then((res) => res.data)
			.then((result) => {
				setUser(result);
			}, []);
	}, []);

	return (
		<div>
			<Header />
			<Card bg={"dark"} text={"white"} className="my-account-card">
				<Card.Header>{user.username}</Card.Header>
				<Card.Body>
					<Card.Title>
						{user.firstname} {user.lastname}
					</Card.Title>
					<Card.Text>
						With this account, you can create update or delete activities.
					</Card.Text>
					<Card.Text>Enjoy !!</Card.Text>
				</Card.Body>
			</Card>
		</div>
	);
};

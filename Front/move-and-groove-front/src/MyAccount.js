import React, { useState, useEffect } from "react";
import Card from "react-bootstrap/Card";
import { userService } from "./userService";
import { Header } from "./Header";

export const MyAccount = () => {
	const [user, setUser] = useState([]);

	useEffect(() => {
		userService
			.getUser(1)
			.then((res) => res.data)
			.then((result) => {
				setUser(result);
			}, []);
	}, []);

	return (
		<div>
			<Header />
			<Card
				bg={"dark"}
				text={"white"}
				style={{ width: "18rem" }}
				className="mb-2"
			>
				<Card.Header>{user.username}</Card.Header>
				<Card.Body>
					<Card.Title>
						{user.firstname} {user.lastname}
					</Card.Title>
					<Card.Text>
						Some quick example text to build on theand make up the bulk of the
						card's content.
					</Card.Text>
				</Card.Body>
			</Card>
		</div>
	);
};

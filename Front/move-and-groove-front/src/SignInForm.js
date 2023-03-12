import React, { useState } from "react";
import { Header } from "./Header";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import { authService } from "./authService";
import "./css/SignInForm.css";

export const SignInForm = () => {
	const [password, setPassword] = useState("");
	const [username, setUsername] = useState("");

	const navigate = useNavigate();

	const login = (e) => {
		e.preventDefault();
		const user = { username, password };
		authService
			.login(user)
			.then((res) => {
				authService.saveToken(res.data.token);
				navigate("/");
			})
			.catch((error) => console.log(error));
	};

	return (
		<div>
			<Header />
			<div className="signInForm">
				<Form>
					<Form.Group className="mb-3" controlId="formGroupUsername">
						<Form.Label>Username</Form.Label>
						<Form.Control
							type="text"
							placeholder="Enter username"
							value={username}
							onChange={(e) => setUsername(e.target.value)}
						/>
					</Form.Group>
					<Form.Group className="mb-3" controlId="formGroupPassword">
						<Form.Label>Password</Form.Label>
						<Form.Control
							type="password"
							placeholder="Enter password"
							value={password}
							onChange={(e) => setPassword(e.target.value)}
						/>
					</Form.Group>
				</Form>
				<Button variant="success" type="submit" onClick={login}>
					Submit
				</Button>
			</div>
		</div>
	);
};

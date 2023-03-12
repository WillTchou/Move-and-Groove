import React, { useState } from "react";
import { Header } from "./Header";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import { authService } from "./authService";

export const SignUpForm = () => {
	const [email, setEmail] = useState("");
	const [firstname, setFirstname] = useState("");
	const [lastname, setLastname] = useState("");
	const [password, setPassword] = useState("");
	const [username, setUsername] = useState("");

	const navigate = useNavigate();

	const subscribe = (e) => {
		e.preventDefault();
		const user = { firstname, lastname, username, email, password };
		authService
			.signUp(user)
			.then((res) => {
				authService.saveToken(res.data.token);
				navigate("/");
			})
			.catch((error) => console.log(error));
	};

	return (
		<div>
			<Header />
			<div className="signUpForm">
				<Form>
					<Row className="mb-3">
						<Form.Group as={Col} controlId="formGridFirstname">
							<Form.Label>Firstname</Form.Label>
							<Form.Control
								type="text"
								placeholder="Enter Firstname"
								value={firstname}
								onChange={(e) => setFirstname(e.target.value)}
							/>
						</Form.Group>
						<Form.Group as={Col} controlId="formGridLastname">
							<Form.Label>Lastname</Form.Label>
							<Form.Control
								type="text"
								placeholder="Enter Lastname"
								value={lastname}
								onChange={(e) => setLastname(e.target.value)}
							/>
						</Form.Group>
					</Row>
					<Form.Group className="mb-3" controlId="formGroupEmail">
						<Form.Label visuallyHidden>Username</Form.Label>
						<InputGroup className="mb-2">
							<InputGroup.Text>@</InputGroup.Text>
							<Form.Control
								type="email"
								placeholder="Email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
							/>
						</InputGroup>
					</Form.Group>
					<Form.Group className="mb-3" controlId="formGroupUsername">
						<Form.Label>Username</Form.Label>
						<Form.Control
							placeholder="Enter username"
							value={username}
							onChange={(e) => setUsername(e.target.value)}
						/>
					</Form.Group>
					<Form.Group className="mb-3" controlId="formGroupPassword">
						<Form.Label>Password</Form.Label>
						<Form.Control
							type="password"
							placeholder="Password"
							value={password}
							onChange={(e) => setPassword(e.target.value)}
						/>
					</Form.Group>
					<Button variant="success" type="submit" onClick={subscribe}>
						Submit
					</Button>
				</Form>
			</div>
		</div>
	);
};

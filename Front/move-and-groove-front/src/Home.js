import React, { useState, useEffect } from "react";
import { Header } from "./Header";
import { ActivityCard } from "./ActivityCard";
import { ActivityForm } from "./ActivityForm";
import Button from "react-bootstrap/Button";
import { authService } from "./authService";
import { activityService } from "./activityService";
import { useNavigate } from "react-router-dom";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import "./css/Home.css";

export const Home = () => {
	const [show, setShow] = useState(false);
	const [activities, setActivities] = useState([]);

	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);

	const navigate = useNavigate();

	const redirectToSignInPage = () => {
		navigate("/SignIn");
	};

	useEffect(() => {
		if (authService.isLogged()) {
			activityService
				.getActivities()
				.then((res) => res.data)
				.then((result) => {
					setActivities(result);
				}, []);
		}
	}, []);

	return (
		<div>
			<Header />
			<div className="activities">
				{authService.isLogged() ? (
					<div className="activity-list">
						<Row xs={1} md={1} className="g-4">
							{activities.map((activity, index) => (
								<Col>
									<ActivityCard key={index} activity={activity} />
								</Col>
							))}
						</Row>
						<div className="create-activity">
							<Button variant="success" onClick={handleShow}>
								Create new activity
							</Button>
							<ActivityForm show={show} handleClose={handleClose} />
						</div>
					</div>
				) : (
					<Button variant="success" onClick={redirectToSignInPage}>
						You need to have an account to create activities
					</Button>
				)}
			</div>
		</div>
	);
};

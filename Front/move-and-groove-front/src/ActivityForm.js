import React, { useState } from "react";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { activityService } from "./activityService";

export const ActivityForm = ({ show, handleClose, activity }) => {
	const [name, setName] = useState(activity ? activity.name : "");
	const [duration, setDuration] = useState(activity ? activity.duration : "");

	const createActivity = (e) => {
		e.preventDefault();
		const activity = { name, duration };
		activityService
			.createActivity(activity)
			.then(() => window.location.reload())
			.catch((error) => console.log(error));
	};

	const updateActivity = (e) => {
		e.preventDefault();
		const newActivity = { name, duration };
		activityService
			.updateActivityById(activity.id, newActivity)
			.then(() => window.location.reload())
			.catch((error) => console.log(error));
	};

	return (
		<div className="activity-form">
			<Modal show={show} onHide={handleClose}>
				<Modal.Header closeButton>
					<Modal.Title>New activity</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<Form>
						<Form.Group className="mb-3" controlId="formActivityName">
							<Form.Label>Activity Name</Form.Label>
							<Form.Control
								type="text"
								placeholder="Enter activity name"
								value={name}
								onChange={(e) => setName(e.target.value)}
							/>
						</Form.Group>
						<Form.Group className="mb-3" controlId="formActivityDuration">
							<Form.Label>Duration</Form.Label>
							<Form.Control
								type="time"
								placeholder="Enter activity duration"
								value={duration}
								onChange={(e) => setDuration(e.target.value)}
								step="2"
							/>
						</Form.Group>
					</Form>
				</Modal.Body>
				<Modal.Footer>
					<Button variant="secondary" onClick={handleClose}>
						Close
					</Button>
					<Button
						variant="success"
						onClick={activity ? updateActivity : createActivity}
						type="submit"
					>
						Create
					</Button>
				</Modal.Footer>
			</Modal>
		</div>
	);
};

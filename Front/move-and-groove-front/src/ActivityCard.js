import React, { useState } from "react";
import Card from "react-bootstrap/Card";
import { ActivityDeletePopin } from "./ActivityDeletePopin";
import Button from "react-bootstrap/Button";
import { ActivityForm } from "./ActivityForm";

export const ActivityCard = ({ activity }) => {
	const [showDelete, setShowDelete] = useState(false);
	const [showUpdate, setShowUpdate] = useState(false);

	const handleCloseDelete = () => setShowDelete(false);
	const handleShowDelete = () => setShowDelete(true);

	const handleCloseUpdate = () => setShowUpdate(false);
	const handleShowUpdate = () => setShowUpdate(true);

	return (
		<div>
			<Card bg="dark" variant="dark" text={"white"}>
				<Card.Header>{activity.name}</Card.Header>
				<Card.Body>
					<Card.Title>{activity.creationDate}</Card.Title>
					<Card.Text>{activity.duration}</Card.Text>
				</Card.Body>
				<Card.Footer>
					<Button variant="danger" onClick={handleShowDelete}>
						Delete activity
					</Button>
					<Button variant="info" onClick={handleShowUpdate}>
						Edit Activity
					</Button>
				</Card.Footer>
			</Card>
			<ActivityDeletePopin
				show={showDelete}
				handleClose={handleCloseDelete}
				activity={activity}
			/>
			<ActivityForm
				show={showUpdate}
				handleClose={handleCloseUpdate}
				activity={activity}
			/>
		</div>
	);
};

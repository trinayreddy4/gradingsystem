import React, { useState, useEffect } from 'react';
import { AppBar, Box, Button, Toolbar, Typography, Paper, Grid, TextField } from '@mui/material';
import axios from 'axios';
import { Worker, Viewer } from '@react-pdf-viewer/core';
import '@react-pdf-viewer/core/lib/styles/index.css';
import mammoth from 'mammoth';

const FacultyGrading = () => {
  const [submissions, setSubmissions] = useState([]);
  const [selectedSubmission, setSelectedSubmission] = useState(null);
  const [grade, setGrade] = useState('');
  const [docContent, setDocContent] = useState(''); // For Word document content

  // Fetch submissions from the backend
  // useEffect(() => {
  //   axios.get('/api/submissions') // Replace with your actual API endpoint
  //     .then(response => {
  //       setSubmissions(response.data);
  //     })
  //     .catch(error => {
  //       console.error("Error fetching submissions:", error);
  //     });
  // }, []);

  const handleGradeChange = (event) => {
    setGrade(event.target.value);
  };

  const handleViewSubmission = async (submission) => {
    setSelectedSubmission(submission); // Display selected submission

    if (submission.fileUrl.endsWith('.docx')) {
      // Fetch and convert DOCX file content to HTML
      const response = await axios.get(submission.fileUrl, { responseType: 'arraybuffer' });
      const buffer = response.data;
      const result = await mammoth.convertToHtml({ arrayBuffer: buffer });
      setDocContent(result.value); // Set the Word document content
    }
  };

  const handleSubmitGrade = (submissionId) => {
    if (grade && submissionId) {
      axios.post(`/api/grade/${submissionId}`, { grade })  // Replace with your actual API endpoint for grading
        .then(response => {
          alert('Grade submitted successfully!');
          setGrade(''); // Clear the grade input after submission
        })
        .catch(error => {
          console.error('Error submitting grade:', error);
        });
    } else {
      alert('Please enter a valid grade.');
    }
  };

  const renderSubmissionViewer = (submission) => {
    if (!submission) return null;

    const { fileUrl } = submission;
    const fileExtension = fileUrl.split('.').pop().toLowerCase();

    if (fileExtension === 'pdf') {
      return (
        <Worker workerUrl={`https://unpkg.com/pdfjs-dist@2.6.347/build/pdf.worker.min.js`}>
          <div style={{ height: '600px' }}>
            <Viewer fileUrl={fileUrl} />
          </div>
        </Worker>
      );
    } else if (fileExtension === 'docx' || fileExtension === 'doc') {
      return (
        <div dangerouslySetInnerHTML={{ __html: docContent }} />
      );
    } else {
      return <Typography>Unsupported file format.</Typography>;
    }
  };

  return (
    <Box sx={{ flex: 1, flexGrow: 1, height: "100vh" }}>
      <AppBar
        sx={{ height: '50px', px: 4, fontWeight: 500, display: 'flex', justifyContent: 'center' }}
        position="static"
      >
        <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
          <Typography variant="h6">Grading System</Typography>
          <Box>
            <Button variant="filled">Courses</Button>
            <Button variant="filled">Submissions</Button>
            <Button variant="filled">Grades</Button>
          </Box>
        </Toolbar>
      </AppBar>

      <Box sx={{ p: 3 }}>
        <Paper elevation={3} sx={{ p: 4, maxWidth: '800px', margin: 'auto' }}>
          <Typography variant="h5" gutterBottom>
            Student Submissions
          </Typography>

          {submissions.length === 0 ? (
            <Typography>No submissions available.</Typography>
          ) : (
            submissions.map((submission) => (
              <Box key={submission.id} sx={{ mb: 4 }}>
                <Paper elevation={2} sx={{ p: 3 }}>
                  <Grid container spacing={2}>
                    <Grid item xs={12}>
                      <Typography variant="h6">
                        {submission.studentName} - {submission.assignmentName}
                      </Typography>
                    </Grid>
                    <Grid item xs={12}>
                      <Typography>
                        Deadline: {submission.deadline}
                      </Typography>
                    </Grid>
                    <Grid item xs={12}>
                      <Button
                        variant="outlined"
                        onClick={() => handleViewSubmission(submission)}
                      >
                        View Submission
                      </Button>
                    </Grid>
                    {selectedSubmission && selectedSubmission.id === submission.id && (
                      <Grid item xs={12}>
                        {renderSubmissionViewer(selectedSubmission)}
                      </Grid>
                    )}
                    <Grid item xs={12}>
                      <TextField
                        label="Grade (out of 100)"
                        type="number"
                        value={grade}
                        onChange={handleGradeChange}
                        variant="outlined"
                        fullWidth
                        inputProps={{ min: 0, max: 100 }}
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <Button
                        variant="contained"
                        color="primary"
                        onClick={() => handleSubmitGrade(submission.id)}
                        fullWidth
                      >
                        Submit Grade
                      </Button>
                    </Grid>
                  </Grid>
                </Paper>
              </Box>
            ))
          )}
        </Paper>
      </Box>
    </Box>
  );
};

export default FacultyGrading;

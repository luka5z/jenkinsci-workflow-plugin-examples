import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException

def timeoutThreshold = 10
def maxCount = 2
def count = 0

def branches = [:]

for (i = 0; i < maxCount; i++) {
    def name="branch-${i}"
    
    branches[name] = {
        node('test-slave') {
            wrap([$class: 'TimestamperBuildWrapper']) {
                count++
                try {
                    timeout(time: timeoutThreshold, unit: 'SECONDS') {
                        waitUntil {count == maxCount}
                    }
                } catch (FlowInterruptedException e) {
                    manager.addWarningBadge("waitUntil ${name} timeout threshold")
                    manager.build.doStop()
                }

                echo "executing ${name}..."
                sleep 5
                echo "done ${name}"
            }
        }
    }	
}

parallel branches

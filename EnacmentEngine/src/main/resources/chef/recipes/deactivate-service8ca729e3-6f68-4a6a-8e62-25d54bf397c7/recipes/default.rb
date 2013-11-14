#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "disable-service8ca729e3-6f68-4a6a-8e62-25d54bf397c7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service8ca729e3-6f68-4a6a-8e62-25d54bf397c7::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['8ca729e3-6f68-4a6a-8e62-25d54bf397c7']['InstallationDir']}/service-8ca729e3-6f68-4a6a-8e62-25d54bf397c7.jar]", :immediately
end

ruby_block "remove-service8ca729e3-6f68-4a6a-8e62-25d54bf397c7" do
  block do
  	node.run_list.remove("recipe[service8ca729e3-6f68-4a6a-8e62-25d54bf397c7::jar]")
  end
  only_if { node.run_list.include?("recipe[service8ca729e3-6f68-4a6a-8e62-25d54bf397c7::jar]") }
end

ruby_block "remove-deactivate-service8ca729e3-6f68-4a6a-8e62-25d54bf397c7" do
  block do
    node.run_list.remove("recipe[deactivate-service8ca729e3-6f68-4a6a-8e62-25d54bf397c7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8ca729e3-6f68-4a6a-8e62-25d54bf397c7]") }
end
